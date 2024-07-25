import hre from 'hardhat';



async function main() {
  const Token = await hre.ethers.getContractFactory('Token');
  console.log('Deploying Token...');
  const [owner] = await hre.ethers.getSigners();
  const token = await Token.deploy(owner.getAddress());
  await token.waitForDeployment();
  console.log('Token deployed to:', await token.getAddress());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
