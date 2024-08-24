import { loadFixture } from '@nomicfoundation/hardhat-toolbox/network-helpers';
import { expect } from 'chai';
import hre from 'hardhat';


describe('Box', function () {
  async function deployFixture() {
    const Box = await hre.ethers.getContractFactory('Box');
    const box = await Box.deploy();
    const defaultValue = 42;
    await box.store(defaultValue);
    return { box, defaultValue };
  }

  describe('Deployment', function () {
    it('Should set the right value', async function () {
      const { box, defaultValue } = await loadFixture(deployFixture);
      expect(await box.retrieve()).to.equal(defaultValue);
    });
  });
});
