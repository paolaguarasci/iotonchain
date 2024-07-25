import { buildModule } from '@nomicfoundation/hardhat-ignition/modules';

export default buildModule('HashModule', (m) => {
  return { box: m.contract('Hash') };
});
