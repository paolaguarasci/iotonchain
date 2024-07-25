import hre from 'hardhat';

async function main() {
  const Token = await hre.ethers.getContractFactory('TokenV2');
  console.log('Deploying Token...');
  const [owner] = await hre.ethers.getSigners();
  const token = await Token.deploy(owner.getAddress());
  // const token = await hre.upgrades.deployProxy(Token, [owner.address]);
  await token.waitForDeployment();
  console.log(
    'Token (trasparent proxy) deployed to:',
    await token.getAddress()
  );
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
