// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

contract Hash {

    event HashSigned(bytes32 indexed hash, address indexed signer, SignData data);

    function initialize() public {}

    function signHash(bytes32 hash, string memory data) public {
        require(hash != bytes32(0), "Hash cannot be zero");
        require(!keyExists[hash], "Hash already signed");
        emit HashSigned(hash, msg.sender, signData[hash]);
    }

    function isHashSigned(bytes32 hash) public view returns (bool) {
        return keyExists[hash];
    }
}
