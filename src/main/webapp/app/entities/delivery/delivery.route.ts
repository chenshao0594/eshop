import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DeliveryComponent } from './delivery.component';
import { DeliveryDetailComponent } from './delivery-detail.component';
import { DeliveryPopupComponent } from './delivery-dialog.component';
import { DeliveryDeletePopupComponent } from './delivery-delete-dialog.component';

import { Principal } from '../../shared';

export const deliveryRoute: Routes = [
  {
    path: 'delivery',
    component: DeliveryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.delivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'delivery/:id',
    component: DeliveryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.delivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deliveryPopupRoute: Routes = [
  {
    path: 'delivery-new',
    component: DeliveryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.delivery.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'delivery/:id/edit',
    component: DeliveryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.delivery.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'delivery/:id/delete',
    component: DeliveryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.delivery.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
