// SPDX-License-Identifier: BUSL-1.1
pragma solidity 0.8.12;

contract BatchTransfer{
    event OwnerChanged(address indexed oldOwner, address indexed newOwner);
    address private _owner;

    error OwnableUnauthorizedAccount(address account);
    error OwnableInvalidOwner(address owner);

    modifier onlyOwner() {
        if (_owner !=msg.sender) {
            revert OwnableUnauthorizedAccount(msg.sender);
        }
        _;
    }
    function owner() external view returns (address) {
        return _owner;
    }
    constructor () payable {
        _owner = msg.sender;
        emit OwnerChanged(address(0), msg.sender);
    }
    function setOwner(address newOwner) external onlyOwner{
        if (newOwner == address(0)) {
            revert OwnableInvalidOwner(address(0));
        }
        emit OwnerChanged(_owner, newOwner);
        _owner = newOwner;
    }
    receive() external payable {
        
    }
    event TransferEthTo(address indexed to, uint256 indexed timestamp, uint256 value);
    function multiSend(bytes memory transactions) external onlyOwner payable {
        //0xEa51342dAbbb928aE1e576bd39eFf8aaf070A8c600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000044a9059cbb0000000000000000000000006d223531a9934296edb6d48c77053548d5545aec0000000000000000000000000000000000000000000000000000000000000000Ea51342dAbbb928aE1e576bd39eFf8aaf070A8c600000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000044a9059cbb0000000000000000000000006d223531a9934296edb6d48c77053548d5545aec00000000000000000000000000000000000000000000000000000000000000006d223531A9934296EDB6D48C77053548d5545Aec000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006d223531A9934296EDB6D48C77053548d5545Aec00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
        assembly {
            let length := mload(transactions)
            let i := 0x20
            for {
            } lt(i, length) {
            } {
                let to := shr(0x60, mload(add(transactions, i)))
                let value := mload(add(transactions, add(i, 0x14)))
                let dataLength := mload(add(transactions, add(i, 0x34)))
                let data := add(transactions, add(i, 0x54))
                let success := call(gas(), to, value, data, dataLength,0, 0)
                if eq(success, 0) {
                    let errorLength := returndatasize()
                    returndatacopy(0, 0, errorLength)
                    revert(0, errorLength)
                }
                if gt(value,0){
                    mstore(0, value)
                    log3(
                        0,
                        0x20,
                        0xf2e65a86952e8860f9295659a6fcd755f1d9cba9a3e084de9d5fd7be5c4190e5,
                        to,
                        timestamp()
                    )
                }
                i := add(i, add(0x54, dataLength))
            }
        }
    }
}
