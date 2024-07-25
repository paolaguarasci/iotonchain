import hre from 'hardhat';
import { Box } from '../typechain-types';
require('dotenv').config();
async function main() {
  const address = process.env.BOX_CONTRACT_ADDRESS as string;
  const Box = await hre.ethers.getContractFactory('Box');
  const box: Box = Box.attach(address);
  await box.store(555);
  const value = await box.retrieve();
  console.log('Box value is', value.toString());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
