// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "@openzeppelin/contracts-upgradeable/access/OwnableUpgradeable.sol";
import "@openzeppelin/contracts-upgradeable/utils/ReentrancyGuardUpgradeable.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract Hash is Initializable, OwnableUpgradeable, ReentrancyGuardUpgradeable {
  struct SignData {
    bytes32 hashSigned;
    uint256 timestamp;
    string data;
  }
  mapping(bytes32 => SignData) public signData;
  mapping(bytes32 => bool) public keyExists;

  event HashSigned(bytes32 indexed hash, address indexed signer, SignData data);

  ///@custom:oz-upgrades-unsafe-allow constructor
  constructor() {
    _disableInitializers();
  }

  function initialize(address initialOwner) public initializer {
    __Ownable_init(initialOwner);

  }

  function signHash(bytes32 hash, string memory data) public onlyOwner nonReentrant {
    require(hash != bytes32(0), "Hash cannot be zero");
    require(!keyExists[hash], "Hash already signed");

    keyExists[hash] = true;
    signData[hash] = SignData({
      hashSigned: hash,
      timestamp: block.timestamp,
      data: data
    });

    emit HashSigned(hash, msg.sender, signData[hash]);
  }

  function isHashSigned(bytes32 hash) public view returns (bool) {
    return keyExists[hash];
  }

  function getSignData(
    bytes32 dataHash
  ) public view returns (bytes32, uint256, string memory) {
    SignData memory record = signData[dataHash];
    require(keyExists[dataHash], "Hash not signed");
    return (record.hashSigned, record.timestamp, record.data);
  }
}
