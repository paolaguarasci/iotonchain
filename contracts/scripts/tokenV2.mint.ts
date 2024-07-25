import hre from 'hardhat';
import { Token } from '../typechain-types';

require('dotenv').config();

async function main() {
  const address = process.env.TOKENV2_NOPROXY_ADDRESS as string;
  const tokenType = 1;
  const Token = await hre.ethers.getContractFactory('TokenV2');
  const token: Token = Token.attach(address);
  const [owner] = await hre.ethers.getSigners();
  await token.mint(owner.getAddress(), tokenType, 10, '0x');
  const value = await token.balanceOf(owner.getAddress(), tokenType);
  console.log(`Token type ${tokenType} value is ${value.toString()}`);
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
