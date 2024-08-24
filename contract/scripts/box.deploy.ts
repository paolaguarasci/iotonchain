import hre from 'hardhat';

async function main() {
  const Box = await hre.ethers.getContractFactory('Box');
  console.log('Deploying BOX...');
  const box = await Box.deploy();
  await box.waitForDeployment();
  console.log('box deployed to:', await box.getAddress());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
