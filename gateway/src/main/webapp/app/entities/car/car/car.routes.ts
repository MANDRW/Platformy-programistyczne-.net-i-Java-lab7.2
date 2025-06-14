import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CarResolve from './route/car-routing-resolve.service';

const carRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/car.component').then(m => m.CarComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/car-detail.component').then(m => m.CarDetailComponent),
    resolve: {
      car: CarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/car-update.component').then(m => m.CarUpdateComponent),
    resolve: {
      car: CarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/car-update.component').then(m => m.CarUpdateComponent),
    resolve: {
      car: CarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default carRoute;
