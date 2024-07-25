import { buildModule } from '@nomicfoundation/hardhat-ignition/modules';

export default buildModule('BoxModule', (m) => {
  return { box: m.contract('Box', [0]) };
});
