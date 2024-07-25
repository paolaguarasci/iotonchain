import hre from 'hardhat';
import { TokenV2 } from '../typechain-types/contracts/TokenV2.sol';
import { ErrorDecoder } from 'ethers-decode-error'
import type { DecodedError } from 'ethers-decode-error'
require('dotenv').config();

async function main() {
  const errorDecoder = ErrorDecoder.create()
  const address = process.env.TOKENV2_NOPROXY_ADDRESS as string;
  const tokenType = 2;
  const Token = await hre.ethers.getContractFactory('TokenV2');
  const token: TokenV2 = Token.attach(address);
  const [account1, account2, account3] = await hre.ethers.getSigners();

  try {
    await token.connect(account2).confirmTransfer(1);
    await token.connect(account3).confirmTransfer(2);
  } catch(err) {
    const decodedError: DecodedError = await errorDecoder.decode(err)
    console.error(`${decodedError.reason}`)
  }

  let value = await token.balanceOf(account1.getAddress(), tokenType);
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
