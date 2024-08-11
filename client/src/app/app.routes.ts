import { mapToCanActivate, Routes } from '@angular/router';
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
import { BatchSingleComponent } from './page/batch-single/batch-single.component';
import { TraceOneBatchPublicComponent } from './page/trace-one-batch-public/trace-one-batch-public.component';
import { AuthGuard } from './utils/auth.guard';

export const routes: Routes = [
  {
    path: 'public-track/:company/:batchid',
    component: TraceOneBatchPublicComponent,
  },
  {
    path: '',
    component: DashboardComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'product',
    component: ComapnyBatchListComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'product/new',
    component: CreateOneBatchComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'document',
    component: DocumentsListComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'product-type',
    component: ProductTypeListComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'production-step',
    component: CreateStepsComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'chain/:id',
    component: LandingChainComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'truck-list',
    component: TruckListComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'track/:id',
    component: TraceOneBatchComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'batch/:id',
    component: BatchSingleComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'transport',
    component: TransportComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'notarize-list',
    component: NotarizeListComponent,
    canActivate: mapToCanActivate([AuthGuard])
  },
  {
    path: 'transfert-list',
    component: TransfertListComponent,
    canActivate: mapToCanActivate([AuthGuard])
  }

];
