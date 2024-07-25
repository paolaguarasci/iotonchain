import hre from 'hardhat';
import { TokenV2 } from '../typechain-types/contracts/TokenV2.sol';
import type { DecodedError } from 'ethers-decode-error'
require('dotenv').config();

async function main() {
  const address = process.env.TOKENV2_NOPROXY_ADDRESS as string;
  const tokenType = 2;
  const Token = await hre.ethers.getContractFactory('TokenV2');
  const token: TokenV2 = Token.attach(address);
  const [account1, account2, account3] = await hre.ethers.getSigners();
  
  await token.mint(account1.getAddress(), 1, 100, []);
  
  await token.mint(account2.getAddress(), 2, 100, []);
  
  await token.mint(account3.getAddress(), 3, 100, []);
  
  let value = await token.balanceOf(account1.getAddress(), tokenType);

  let amount = 10

  await token.connect(account1).initiateTransfer(account2,  1,amount);
  console.log(`ACCOUNT1 send ${amount} token 1 to ACCOUNT2`)
  
  await token.connect(account2).initiateTransfer(account3,  2,amount);
  console.log(`ACCOUNT2 send ${amount} token 2 to ACCOUNT3`)
  

  value = await token.balanceOf(account1.getAddress(), tokenType);
  console.log(`ACCOUNT1 Token type ${tokenType} value is ${value.toString()}`);

  value = await token.balanceOf(account2.getAddress(), tokenType);
  console.log(`ACCOUNT2 Token type ${tokenType} value is ${value.toString()}`);

  value = await token.balanceOf(account3.getAddress(), tokenType);
  console.log(`ACCOUNT3 Token type ${tokenType} value is ${value.toString()}`);

}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
