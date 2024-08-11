import hre from 'hardhat';
import { Hash } from '../typechain-types';
require('dotenv').config();
async function main() {
  const Hash = await hre.ethers.getContractFactory('Hash');
  const proxyAddress = process.env.HASH_PROXY_ADDRESS as string;
  const hashContract: Hash = Hash.attach(proxyAddress);
  hashContract.on('HashSigned', (hash, signer, data) => {
    console.table({
      signer: signer,
      hash: hash,
      data: data[2]
    });
  });
  console.log('In ascolto sugli eventi di firma del contratto...');
}

main();
