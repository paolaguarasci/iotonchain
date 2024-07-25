import { loadFixture } from '@nomicfoundation/hardhat-toolbox/network-helpers';
import { expect } from 'chai';
import hre from 'hardhat';

describe('Box', function () {
  async function deployFixture() {
    const storeValue = 42;
    const [owner, otherAccount] = await hre.ethers.getSigners();
    const Box = await hre.ethers.getContractFactory('Box');
    const box = await Box.deploy(storeValue);

    return { box: box, storeValue: storeValue, owner, otherAccount };
  }

  describe('Deployment', function () {
    it('Should set the right value', async function () {
      const { box, storeValue } = await loadFixture(deployFixture);
      expect(await box.retrieve()).to.equal(storeValue);
    });

    it('Should set the right owner', async function () {
      const { box, owner } = await loadFixture(deployFixture);
      expect(await box.owner()).to.equal(owner.address);
    });

    it('Should revert with the right error if called from another account', async function () {
      const { box, otherAccount } = await loadFixture(deployFixture);
      await expect(
        box.connect(otherAccount).store(42)
      ).to.be.revertedWithCustomError(box, 'OwnableUnauthorizedAccount');
    });
  });

  describe('Store', function () {
    it('Should set the right value', async function () {
      const { box } = await loadFixture(deployFixture);
      const newStoreValue = 555;
      await box.store(newStoreValue);
      expect(await box.retrieve()).to.equal(newStoreValue);
    });

    it('store emits an event', async function () {
      const { box, storeValue } = await loadFixture(deployFixture);
      await expect(await box.store(storeValue)).to.emit(box, 'ValueChanged');
    });
  });
});
