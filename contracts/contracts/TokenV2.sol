// contracts/Token.sol
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";
import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Pausable.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Burnable.sol";
import {ERC1155Holder} from "@openzeppelin/contracts/token/ERC1155/utils/ERC1155Holder.sol";

// le cifre dopo la virgola sono massimo 3
// tutti i numeri sono moltiplicati per 1000

contract TokenV2 is
    ERC1155,
    ERC1155Pausable,
    ERC1155Burnable,
    ReentrancyGuard,
    ERC1155Holder,
    Ownable
{
    struct myTokenData {
        string mySecret;
    }

    struct productToBurn {
        uint prod;
        // prima o poi deve diventare un dato in virgola fissa (non ha la virgola mobile!)
        uint256 quantity;
    }

    // constructor(address initialOwner) ERC1155("") Ownable(initialOwner) {}
    constructor(string memory uri) ERC1155(uri) Ownable(msg.sender) {}

    function setURI(string memory newuri) public onlyOwner {
        _setURI(newuri);
    }

    function pause() public onlyOwner {
        _pause();
    }

    function unpause() public onlyOwner {
        _unpause();
    }

    function mint(
        address account,
        uint256 id,
        uint256 amount,
        productToBurn[] memory toburn
    ) public onlyOwner {
        // mi devo tenere una struttura dati da usare qui con:
        // tokenType (l'id), amount
        if(toburn.length > 0) {
        for (uint256 i = 0; i < toburn.length; i++) {
            require(
                balanceOf(account, toburn[i].prod) >= toburn[i].quantity,
                "Insufficient raw material balance"
            );
        }
        for (uint256 i = 0; i < toburn.length; i++) {
            _burn(account, toburn[i].prod, toburn[i].quantity);
        }
        }
        // string memory a = "ciao";
        // string.concat(a, "cccc");
        _mint(account, id, amount, arrayStructToBytes(toburn));
    }

    // Convert a struct to bytes
    function structToBytes(
        productToBurn memory _myStruct
    ) internal pure returns (bytes memory) {
        return abi.encode(_myStruct.prod, _myStruct.quantity);
    }

    function arrayStructToBytes(
        productToBurn[] memory _arrayStruct
    ) public pure returns (bytes memory) {
        bytes memory result;

        for (uint i = 0; i < _arrayStruct.length; i++) {
            result = abi.encodePacked(result, structToBytes(_arrayStruct[i]));
        }

        return result;
    }

    function bytesToStruct(
        bytes memory _data
    ) internal pure returns (productToBurn memory) {
        (uint prod, uint256 quantity) = abi.decode(_data, (uint, uint256));
        return productToBurn(prod, quantity);
    }

    function bytesToArrayStruct(
        bytes memory _data
    ) public pure returns (productToBurn[] memory) {
        uint256 numStructs = _data.length / (32 + 32); // Approximate length of each struct (uint256 + address + string)
        productToBurn[] memory arrayStruct = new productToBurn[](numStructs);

        uint256 offset = 0;
        for (uint256 i = 0; i < numStructs; i++) {
            bytes memory structBytes = new bytes(32 + 32);
            for (uint256 j = 0; j < structBytes.length; j++) {
                structBytes[j] = _data[offset + j];
            }
            arrayStruct[i] = bytesToStruct(structBytes);
            offset += structBytes.length;
        }

        return arrayStruct;
    }

    function mintBatch(
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) public onlyOwner {
        _mintBatch(to, ids, amounts, data);
    }

    // The following functions are overrides required by Solidity.

    function _update(
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory values
    ) internal override(ERC1155, ERC1155Pausable) {
        super._update(from, to, ids, values);
    }

    struct PendingTransfer {
        address from;
        address to;
        uint256 id;
        uint256 amount;
        bool confirmed;
    }

    mapping(uint256 => PendingTransfer) public pendingTransfers;
    uint256 public pendingTransferCount;

    event TransferInitiated(
        uint256 indexed transferId,
        address indexed from,
        address indexed to,
        uint256 id,
        uint256 amount
    );
    event TransferConfirmed(
        uint256 indexed transferId,
        address indexed from,
        address indexed to,
        uint256 id,
        uint256 amount
    );
    event TransferCancelled(
        uint256 indexed transferId,
        address indexed from,
        address indexed to,
        uint256 id,
        uint256 amount
    );

    // Funzione per iniziare un trasferimento in due step
    function initiateTransfer(
        address to,
        uint256 id,
        uint256 amount
    ) external nonReentrant {
        require(balanceOf(msg.sender, id) >= amount, "Insufficient balance");
        require(to != address(0), "Invalid recipient");
        pendingTransferCount++;
        pendingTransfers[pendingTransferCount] = PendingTransfer(
            msg.sender,
            to,
            id,
            amount,
            false
        );

        // Blocco temporaneo dei token trasferendoli al contratto stesso
        // in data possiamo mettere un json con dati aggiuntivi hashati eventualmente
        // l'id del prodotto dovrebbe essere, dovremmo legarlo al lotto e alla tipologia di prodotto
        // il mapping possiamo farlo lato applicativo (è meglio!)
        _safeTransferFrom(msg.sender, address(this), id, amount, "");

        emit TransferInitiated(
            pendingTransferCount,
            msg.sender,
            to,
            id,
            amount
        );
    }

    // Funzione per confermare il trasferimento da parte del destinatario
    function confirmTransfer(uint256 transferId) external nonReentrant {
        PendingTransfer storage transfer = pendingTransfers[transferId];
        require(
            transfer.to == msg.sender,
            "Only the intended recipient can confirm this transfer"
        );
        require(!transfer.confirmed, "Transfer already confirmed");
        require(
            balanceOf(address(this), transfer.id) >= transfer.amount,
            "Insufficient contract balance"
        );

        transfer.confirmed = true;

        // Trasferimento dei token dal contratto al destinatario
        _safeTransferFrom(
            address(this),
            transfer.to,
            transfer.id,
            transfer.amount,
            ""
        );

        emit TransferConfirmed(
            transferId,
            transfer.from,
            transfer.to,
            transfer.id,
            transfer.amount
        );

        // Rimozione dei dati del trasferimento per risparmiare gas
        delete pendingTransfers[transferId];
    }

    // Funzione per annullare il trasferimento da parte del mittente
    function cancelTransfer(uint256 transferId) external nonReentrant {
        PendingTransfer storage transfer = pendingTransfers[transferId];
        require(
            transfer.from == msg.sender,
            "Only the sender can cancel this transfer"
        );
        require(!transfer.confirmed, "Cannot cancel a confirmed transfer");

        // Ritorno dei token bloccati al mittente
        _safeTransferFrom(
            address(this),
            transfer.from,
            transfer.id,
            transfer.amount,
            ""
        );
        delete pendingTransfers[transferId];

        emit TransferCancelled(
            transferId,
            transfer.from,
            transfer.to,
            transfer.id,
            transfer.amount
        );
    }

    // Funzione di emergenza per ritirare token bloccati dal contratto
    function emergencyWithdraw(
        uint256 id,
        uint256 amount
    ) external onlyOwner nonReentrant {
        require(
            balanceOf(address(this), id) >= amount,
            "Insufficient contract balance"
        );
        _safeTransferFrom(address(this), owner(), id, amount, "");
    }

    function supportsInterface(
        bytes4 interfaceId
    ) public view virtual override(ERC1155, ERC1155Holder) returns (bool) {
        return (ERC1155.supportsInterface(interfaceId) ||
            ERC1155Holder.supportsInterface(interfaceId));
    }
}
