import { loadFixture } from '@nomicfoundation/hardhat-toolbox/network-helpers';
import { expect } from 'chai';
import { SHA256, enc } from 'crypto-js';
import hre from 'hardhat';

describe('Hash', function () {
  async function deployFixture() {
    const data = {
      data: 'This is my dataaaa',
      timestampInternal: new Date()
    };
    const [owner, otherAccount] = await hre.ethers.getSigners();
    const Hash = await hre.ethers.getContractFactory('Hash');
    const hash = await Hash.deploy();

    const hexHash = SHA256(JSON.stringify(data));
    const hashForSolidity = '0x' + hexHash.toString(enc.Hex);
//    await hash.signHash(hashForSolidity, JSON.stringify(data));

    return { hash, data, hashdata: hashForSolidity, owner, otherAccount };
  }

  describe('Deployment', function () {
    // it('Should set the right owner', async function () {
    //   const { hash, owner } = await loadFixture(deployFixture);
    //   expect(await hash.owner()).to.equal(owner.address);
    // });
  });

  describe('Hash', function () {
    // it('Should set the right value', async function () {
    //   const { hash, data, hashdata } = await loadFixture(deployFixture);
    //   await hash.signHash(hashdata, JSON.stringify(data));
    //   expect(await hash.isHashSigned(hashdata)).to.be.true;
    // });

    it('Should get error if try to sign same hash', async function () {
      const { hash, data, hashdata } = await loadFixture(deployFixture);
      await hash.signHash(hashdata, JSON.stringify(data));
      await expect(
        await hash.signHash(hashdata, JSON.stringify(data))
      ).to.be.revertedWith('Hash already signed');
    });

    // it('hash emits an event', async function () {
    //   const { hash, data, hashdata } = await loadFixture(deployFixture);
    //   expect(await hash.signHash(hashdata, JSON.stringify(data))).to.emit(
    //     hash,
    //     'HashSigned'
    //   );
    // });

    //

    // it('Should revert with the right error if called hash func from another account', async function () {
    //   const { hash, otherAccount, data, hashdata } = await loadFixture(
    //     deployFixture
    //   );
    //   await expect(
    //     hash.connect(otherAccount).signHash(hashdata, JSON.stringify(data))
    //   ).to.be.revertedWithCustomError(hash, 'OwnableUnauthorizedAccount1');
    // });

    // it('Should be able to access the check function from another account', async function () {
    //   const { hash, otherAccount, data, hashdata } = await loadFixture(
    //     deployFixture
    //   );

    //   await hash.signHash(hashdata, JSON.stringify(data));

    //   await expect(
    //     await hash.connect(otherAccount).isHashSigned(hashdata)
    //   ).to.be.revertedWithCustomError(hash, 'OwnableUnauthorizedAccount');
    // });
  });
});
