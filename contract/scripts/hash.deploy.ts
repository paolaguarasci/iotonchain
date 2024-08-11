import { args } from '@openzeppelin/cli/lib/commands/deploy/spec';
import hre from 'hardhat';

async function main() {
  const Hash = await hre.ethers.getContractFactory('Hash');
  console.log('Deploying Hash...');
  const [owner] = await hre.ethers.getSigners();
  let addr = await owner.getAddress()
  let addrNoOx = addr.slice(2);
  const hash = await hre.upgrades.deployProxy(Hash, [addrNoOx]);
  await hash.waitForDeployment();
  console.log('Hash deployed to:', await hash.getAddress());
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
