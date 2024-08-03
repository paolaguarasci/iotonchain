import { Routes } from '@angular/router';
import { CreateStepsComponent } from './page/create-steps/create-steps.component';
import { ComapnyBatchListComponent } from './page/batch-list/product-list.component';
import { ProductTypeListComponent } from './page/product-type-list/product-type-list.component';
import { DashboardComponent } from './page/dashboard/dashboard.component';
import { DocumentsListComponent } from './page/documents-list/documents-list.component';
import { LandingChainComponent } from './page/landing-chain/landing-chain.component';
import { TraceOneBatchComponent } from './page/trace-one-batch/trace-one-batch.component';
import { CreateOneBatchComponent } from './page/create-one-batch/create-one-batch.component';
import { TransportComponent } from './page/transport/transport.component';
import { TruckListComponent } from './page/truck-list/truck-list.component';
import { NotarizeListComponent } from './page/notarize-list/notarize-list.component';
import { TransfertListComponent } from './page/transfert-list/transfert-list.component';

export const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'product',
    component: ComapnyBatchListComponent,
  },
  {
    path: 'product/new',
    component: CreateOneBatchComponent,
  },
  {
    path: 'document',
    component: DocumentsListComponent,
  },
  {
    path: 'product-type',
    component: ProductTypeListComponent,
  },
  {
    path: 'production-step',
    component: CreateStepsComponent,
  },
  {
    path: 'chain/:id',
    component: LandingChainComponent,
  },
  {
    path: 'truck-list',
    component: TruckListComponent,
  },
  {
    path: 'track/:id',
    component: TraceOneBatchComponent,
  },
  {
    path: 'transport',
    component: TransportComponent,
  },
  {
    path: 'notarize-list',
    component: NotarizeListComponent
  },
  {
    path: 'transfert-list',
    component: TransfertListComponent
  }

];
