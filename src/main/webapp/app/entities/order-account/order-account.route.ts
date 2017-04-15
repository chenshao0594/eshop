import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderAccountComponent } from './order-account.component';
import { OrderAccountDetailComponent } from './order-account-detail.component';
import { OrderAccountPopupComponent, OrderAccountDialogComponent } from './order-account-dialog.component';
import { OrderAccountDeletePopupComponent } from './order-account-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderAccountResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const orderAccountRoute: Routes = [
  {
    path: 'order-account',
    component: OrderAccountComponent,
    resolve: {
      'pagingParams': OrderAccountResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-account-new',
    component: OrderAccountDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccount.home.title'
    },
  }, {
    path: 'order-account/:id',
    component: OrderAccountDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderAccountPopupRoute: Routes = [
  {
    path: 'order-account/:id/edit',
    component: OrderAccountPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-account/:id/delete',
    component: OrderAccountDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
