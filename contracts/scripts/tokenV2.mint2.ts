import hre from 'hardhat';
import { Token } from '../typechain-types';
import { ErrorDecoder } from 'ethers-decode-error'
import type { DecodedError } from 'ethers-decode-error'
require('dotenv').config();

async function main() {
  const address = process.env.TOKENV2_NOPROXY_ADDRESS as string;
  const errorDecoder = ErrorDecoder.create()
  const tokenType = 1;
  const Token = await hre.ethers.getContractFactory('TokenV2');
  const token: Token = Token.attach(address);
  const [account1, account2, account3] = await hre.ethers.getSigners();

  
  console.log(`Sono account${}`)
  

  console.log('Situazione prima della produzione con bruciatura');
  for (let i = 1; i <= 3; i++) {
    let value = await token.balanceOf(account3.getAddress(), i);
    console.log(`Token type ${i} value is ${value.toString()}`);
  }

  let productsToBurn = [
    {
      prod: 1,
      quantity: 2
    },
    {
      prod: 2,
      quantity: 3
    }
  ];

  try {
    await token.mint(account3.getAddress(), 3, 1, productsToBurn);
  } catch (err) {
  
    const decodedError: DecodedError = await errorDecoder.decode(err);
    console.error(`${decodedError.reason}`);
  
  }

  console.log('Situazione dopo la produzione con bruciatura');
  for (let i = 1; i <= 3; i++) {
    let value = await token.balanceOf(account3.getAddress(), i);
    console.log(`Token type ${i} value is ${value.toString()}`);
  }
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
