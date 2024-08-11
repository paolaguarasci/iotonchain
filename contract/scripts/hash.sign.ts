import hre from 'hardhat';
import sha256 from 'crypto-js/sha256';
import {enc} from 'crypto-js';
import {Hash} from '../typechain-types';

require('dotenv').config();

async function main() {
  const Hash = await hre.ethers.getContractFactory('Hash');
  console.log('Sign an hash for message');
  const proxyAddress = process.env.HASH_PROXY_ADDRESS as string;
  // @ts-ignore
  const hash: Hash = Hash.attach(proxyAddress);

  let msg = JSON.stringify({
    data: 'This is my data! ' + (Math.floor(Math.random() * 9999) + 1000),
    timestampInternal: new Date().toLocaleString( 'sv', { timeZoneName: 'short' } )
  });
  const hexHash = sha256(msg);
  const hashForSolidity = '0x' + hexHash.toString(enc.Hex);
  console.log(hashForSolidity);
  await hash.signHash(hashForSolidity, msg);
  try {
  } catch (err) {
    console.log(`${hashForSolidity} is alredy signed`);
  }
  let check = await hash.isHashSigned(hashForSolidity);
  console.log('is signed? ', check);
  console.log('when? ', await getTimestampForSign(hash, hashForSolidity));
  await getContractEvents(hash);
}

async function getContractEvents(contracts: Hash) {
  const currentBlock: any = await hre.ethers.provider.getBlockNumber();
  const eventFilter = contracts.filters.HashSigned();
  const events = await contracts.queryFilter(eventFilter, currentBlock - 5000, currentBlock);
  console.log(`Ci sono ${events.length} eventi di firma hash`);
  for (let event of events) {
    let block = await hre.ethers.provider.getBlock(event.blockNumber);
    let data = await getData(contracts, event.args[0]);
    // @ts-ignore
    console.log(`${getDateFromTimeStamp(block.timestamp)} - Hash signed: ${JSON.stringify(event.args[0])} - Data Signed: ${data[2]}`);
  }
}

function getDateFromTimeStamp(ts: any) {
  let time: number = Number(ts) * 1000;
  return new Date(time).toLocaleString( 'sv', { timeZoneName: 'short' } );
}

async function getTimestampForSign(contract: any, hashString: any) {
  try {
    let hashData = await contract.getSignData(hashString);
    return getDateFromTimeStamp(hashData[1]);
  } catch (err) {
    return null;
  }
}

async function getData(contract: any, hashString: any) {
  try {
    return await contract.getSignData(hashString);
  } catch (err) {
    return null;
  }
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
