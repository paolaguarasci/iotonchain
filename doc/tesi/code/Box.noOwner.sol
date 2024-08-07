// contracts/Box.sol
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

contract Box {
    uint256 private _value;

    event ValueChanged(uint256 value);

    constructor(uint256 value) {
        store(value);
    }

    function store(uint256 value) public onlyOwner {
        _value = value;
        emit ValueChanged(value);
    }

    function retrieve() public view returns (uint256) {
        return _value;
    }
}
