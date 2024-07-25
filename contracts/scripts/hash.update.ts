import hre from 'hardhat';

require('dotenv').config();

async function main() {
  const Hash = await hre.ethers.getContractFactory('Hash');
  const proxyAddress = process.env.HASH_PROXY_ADDRESS as string;
  console.log('Update Hash...');
  const hash = await hre.upgrades.upgradeProxy(proxyAddress, Hash);
  console.log('Hash updated! - ', await hash.getAddress());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
