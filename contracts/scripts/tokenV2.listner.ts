import hre from 'hardhat';
import { Token } from '../typechain-types';

require('dotenv').config();

async function main() {
  const address = process.env.TOKENV2_NOPROXY_ADDRESS as string;
  const tokenType = 1;
  const Token = await hre.ethers.getContractFactory('TokenV2');
  const token: Token = Token.attach(address);
  const [owner, otherAccount1, otherAccount2] = await hre.ethers.getSigners();

  token
    .connect(otherAccount1)
    .on('TransferInitiated', (transferId, from, to, id, amount) => {
      console.table({
        transferId,
        from,
        to,
        id,
        amount
      });
    });

  console.log(
    `In ascolto sugli eventi di inizio trasferimento (da accettare) verso ${otherAccount1.address}`
  );
}

main();
