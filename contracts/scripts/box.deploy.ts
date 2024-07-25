import hre from 'hardhat';
async function main() {
  const Box = await hre.ethers.getContractFactory('Box');
  console.log('Deploying Box...');
  const box = await Box.deploy(42);
  await box.waitForDeployment();
  console.log('Box deployed to:', await box.getAddress());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
