import { buildModule } from '@nomicfoundation/hardhat-ignition/modules';

// 0x9415083Bc9FB64F43FBD1c7D55125f4bedA33c07 su polygon amoy
// 0.8199175 MATIC -> 0.42701467383499997 EURO

export default buildModule('Token', (m) => {
  return { token: m.contract('Token', [m.getAccount(0)]) };
});
