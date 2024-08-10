import { loadFixture } from '@nomicfoundation/hardhat-toolbox/network-helpers';
import { expect } from 'chai';
import { SHA256, enc } from 'crypto-js';
import { cloneDeep } from 'lodash'
import hre from 'hardhat';

function getHashFromJson(str: any) {
  const hexHash = SHA256(JSON.stringify(str));
  return '0x' + hexHash.toString(enc.Hex);
}

const data = {
  data: 'This is my dataaaa',
  timestampInternal: new Date()
};

describe('Hash', function () {
  async function deployFixture() {
    const [owner, otherAccount] = await hre.ethers.getSigners();
    const Hash = await hre.ethers.getContractFactory('Hash');

    let addr = await owner.getAddress()
    let addrNoOx = addr.slice(2);

    const hash = await hre.upgrades.deployProxy(Hash, [addrNoOx]);
    const dataNow = cloneDeep(data);
    dataNow.data += Date.now().toString();
    const hashForSolidity = getHashFromJson(dataNow);
    await hash.signHash(hashForSolidity, JSON.stringify(dataNow));

    return { hash, data, hashdata: hashForSolidity, owner, otherAccount, dataNow };
  }

  describe('Deployment', function () {
    it('Should set the right owner', async function () {
      const { hash, owner } = await loadFixture(deployFixture);
      expect(await hash.owner()).to.equal(owner.address);
    });
  });

  describe('Hash', function () {
    it('Should set the right value', async function () {
      const { hash } = await loadFixture(deployFixture);
      let dataNow = cloneDeep(data);
      dataNow.data += Date.now().toString();
      let hashdata = getHashFromJson(dataNow)
      await hash.signHash(hashdata, JSON.stringify(hashdata));
      expect(await hash.isHashSigned(hashdata)).to.be.true;
    });

    it('Should get error if try to sign same hash', async function () {
      const { hash, data } = await loadFixture(deployFixture);
      let dataNow = cloneDeep(data);
      dataNow.data += Date.now().toString();
      let hashdata = getHashFromJson(dataNow)
      await hash.signHash(hashdata, JSON.stringify(hashdata));
      await expect(
        hash.signHash(hashdata, JSON.stringify(hashdata))
      ).to.be.revertedWith('Hash already signed');
    });

    it('hash emits an event', async function () {
      const { hash } = await loadFixture(deployFixture);


      let dataNow = cloneDeep(data);
      dataNow.data += Date.now().toString();
      let hashdata = getHashFromJson(dataNow)

      await expect(hash.signHash(hashdata, JSON.stringify(hashdata))).to.emit(
        hash,
        'HashSigned'
      );
    });

    // npx hardhat coverage --testfiles "test/hash.test.ts"

    it('Should revert with the right error if called hash func from another account', async function () {
      const { hash, otherAccount } = await loadFixture(
        deployFixture
      );

      let dataNow = cloneDeep(data);
      dataNow.data += Date.now().toString();
      let hashdata = getHashFromJson(dataNow);
      await expect(
        hash.connect(otherAccount).signHash(hashdata, JSON.stringify(hashdata))
      ).to.be.revertedWithCustomError(hash, "OwnableUnauthorizedAccount");
    });

    it('Should be able to access the check function from another account', async function () {
      const { hash, otherAccount, data, hashdata } = await loadFixture(
        deployFixture
      );
      expect(await hash.connect(otherAccount).isHashSigned(hashdata)).to.be.true;
    });


    it('Should be able to get hashed data', async function () {
      const { hash, hashdata, dataNow } = await loadFixture(deployFixture);
      let dataFromGetter = await hash.getSignData(hashdata);
      expect(JSON.stringify(dataNow)).to.equal(dataFromGetter[2]);
    });


    it('Should get error if try to get not alredy hashed data', async function () {
      const { hash, hashdata, dataNow } = await loadFixture(deployFixture);
      await expect(
        hash.getSignData(hashdata.replace("3", "4"))
      ).to.be.revertedWith('Hash not signed');
    });

  });
});
