import hre from 'hardhat';

async function main() {
  const Hash = await hre.ethers.getContractFactory('Hash');
  console.log('Deploying Hash...');
  const hash = await hre.upgrades.deployProxy(Hash);
  await hash.waitForDeployment();
  console.log('Hash deployed to:', await hash.getAddress());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
