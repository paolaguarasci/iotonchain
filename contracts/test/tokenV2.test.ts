import { loadFixture } from '@nomicfoundation/hardhat-toolbox/network-helpers';
import { expect } from 'chai';
import hre from 'hardhat';
import { TokenV2 } from '../typechain-types';

describe('TokenV2', function() {
  async function deployFixture() {
    const tokenType = 1;
    const tokenAmount = 100;
    const nullData = '0x';

    const [owner, otherAccount] = await hre.ethers.getSigners();
    const Token1155A = await hre.ethers.getContractFactory('TokenV2');
    const token1155A = await Token1155A.deploy('');

    let productsToBurn = [{
        prod: 1,
        quantity: 10
      }];

    await token1155A.mint(owner, tokenType, tokenAmount, []);
    // await token1155A.mint(owner, 2, 1, productsToBurn);

    return {
      token: token1155A,
      tokenType,
      tokenAmount,
      owner,
      otherAccount,
      nullData,
      productsToBurn
    };
  }

  describe('Deployment', function() {
    it('Should set the correct owner', async function() {
      const { token, owner } = await loadFixture(deployFixture);
      expect(await token.owner()).to.equal(owner.address);
    });

    it('Should allow owner to pause and unpause the contract', async function() {
      const {
        token,
        owner,
        tokenType,
        tokenAmount,
        nullData
      } = await loadFixture(deployFixture);
      await token.pause();
      await expect(
        token.mint(owner, tokenType, tokenAmount, [])
      ).to.be.revertedWithCustomError(token, 'EnforcedPause');
      await token.unpause();
      await token.mint(owner, tokenType, tokenAmount, []);
      expect(await token.balanceOf(owner, tokenType)).to.equal(tokenAmount * 2);
    });

    it('Should allow owner to update URI', async function() {
      const { token } = await loadFixture(deployFixture);
      const newURI = 'https://newuri.com';
      await token.setURI(newURI);
      expect(await token.uri(0)).to.equal(newURI);
    });
  });

  describe('Mint', function() {
    it('Should mint the right value', async function() {
      const {
        token,
        owner,
        tokenType,
        tokenAmount,
        nullData
      } = await loadFixture(deployFixture);
      const tokenToMint = 10;
      await token.mint(owner, tokenType, tokenToMint, []);

      expect(await token.balanceOf(owner, tokenType)).to.equal(
        tokenAmount + tokenToMint
      );
    });

    it('Il token deve avere riferimenti ad eventuali token bruciati necessari per la sua creazione', async function() {});

    it('Should allow owner to mint batch tokens', async function() {
      const {
        token,
        owner,
        tokenType,
        tokenAmount,
        nullData
      } = await loadFixture(deployFixture);
      const tokenToMint = 10;
      await token.mintBatch(
        owner,
        [tokenType, 2],
        [tokenToMint, tokenToMint],
        nullData
      );

      expect(await token.balanceOf(owner, tokenType)).to.equal(
        tokenAmount + tokenToMint
      );
      expect(await token.balanceOf(owner, 2)).to.equal(tokenToMint);
    });

    it('Should be able to call mint as owner for another account', async function() {
      const {
        token,
        otherAccount,
        tokenType,
        tokenAmount,
        nullData
      } = await loadFixture(deployFixture);

      const oldValue = await token.balanceOf(otherAccount, tokenType);
      await token.mint(otherAccount, tokenType, tokenAmount, []);

      expect(await token.balanceOf(otherAccount, tokenType)).to.equal(
        oldValue + BigInt(tokenAmount)
      );
    });

    it('Should revert with the right error if mint is called from another account', async function() {
      const {
        token,
        otherAccount,
        tokenType,
        tokenAmount,
        nullData
      } = await loadFixture(deployFixture);
      await expect(
        token
          .connect(otherAccount)
          .mint(otherAccount, tokenType, tokenAmount, [])
      ).to.be.revertedWithCustomError(token, 'OwnableUnauthorizedAccount');
    });
  });

  describe('Burn', function() {
    it('Should revert with the right error if burn is called from another account', async function() {
      const { token, owner, otherAccount, tokenType } = await loadFixture(
        deployFixture
      );
      const tokenToBurn = 10;
      await expect(
        token.connect(otherAccount).burn(owner, tokenType, tokenToBurn)
      ).to.be.revertedWithCustomError(token, 'ERC1155MissingApprovalForAll');
    });

    it('Should burn token if burn fuction is called from token owner', async function() {
      const { token, owner, tokenType, tokenAmount } = await loadFixture(
        deployFixture
      );
      const tokenToBurn = 10;
      await token.burn(owner, tokenType, tokenToBurn);
      const newValue = await token.balanceOf(owner, tokenType);
      expect(newValue).to.equal(BigInt(tokenAmount) - BigInt(tokenToBurn));
    });
  });

  describe('Transfert One Step', function() {
    it('Should be able to transfer my tokens', async function() {
      const {
        token,
        owner,
        tokenType,
        otherAccount,
        tokenAmount
      } = await loadFixture(deployFixture);
      const tokenToTransfert = 5;
      await token.safeTransferFrom(
        owner,
        otherAccount,
        tokenType,
        tokenToTransfert,
        '0x'
      );
      expect(await token.balanceOf(owner, tokenType)).to.be.equal(
        BigInt(tokenAmount) - BigInt(tokenToTransfert)
      );
    });

    it('Other account should have received my token', async function() {
      const { token, owner, tokenType, otherAccount } = await loadFixture(
        deployFixture
      );

      const tokenToTransfert = 5;
      const oldValue = await token.balanceOf(otherAccount, tokenType);
      await token.safeTransferFrom(
        owner,
        otherAccount,
        tokenType,
        tokenToTransfert,
        '0x'
      );
      expect(await token.balanceOf(otherAccount, tokenType)).to.be.equal(
        oldValue + BigInt(tokenToTransfert)
      );
    });

    it('Should receive the right error if I try to transfer tokens I do not possess', async function() {
      const { token, owner, otherAccount } = await loadFixture(deployFixture);
      const tokenTypeToTransfert = 2;
      const tokenToTransfert = 5;
      await expect(
        token.safeTransferFrom(
          owner,
          otherAccount,
          tokenTypeToTransfert,
          tokenToTransfert,
          '0x'
        )
      ).to.be.revertedWithCustomError(token, 'ERC1155InsufficientBalance');
    });

    it('Should receive the right error if I try to transfer an amount of tokens I do not possess', async function() {
      const {
        token,
        owner,
        otherAccount,
        tokenType,
        tokenAmount
      } = await loadFixture(deployFixture);
      const tokenToTransfert = tokenAmount + 1;
      await expect(
        token.safeTransferFrom(
          owner,
          otherAccount,
          tokenType,
          tokenToTransfert,
          '0x'
        )
      ).to.be.revertedWithCustomError(token, 'ERC1155InsufficientBalance');
    });
  });

  describe('Transfert Two Step', function() {
    it('Should be able to transfer in 2 step - init transfer', async function() {
      const {
        token,
        owner,
        tokenType,
        otherAccount,
        tokenAmount
      } = await loadFixture(deployFixture);
      const tokenToTransfert = 5;
      await token.initiateTransfer(otherAccount, tokenType, tokenToTransfert);
      expect(await token.balanceOf(owner, tokenType)).to.be.equal(
        BigInt(tokenAmount) - BigInt(tokenToTransfert)
      );
    });

    // it('Other account should have received my token', async function () {
    //   const { token, owner, tokenType, otherAccount } = await loadFixture(
    //     deployFixture
    //   );

    //   const tokenToTransfert = 5;
    //   const oldValue = await token.balanceOf(otherAccount, tokenType);
    //   await token.safeTransferFrom(
    //     owner,
    //     otherAccount,
    //     tokenType,
    //     tokenToTransfert,
    //     '0x'
    //   );
    //   expect(await token.balanceOf(otherAccount, tokenType)).to.be.equal(
    //     oldValue + BigInt(tokenToTransfert)
    //   );
    // });
  });
});
