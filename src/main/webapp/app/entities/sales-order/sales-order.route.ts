import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SalesOrderComponent } from './sales-order.component';
import { SalesOrderDetailComponent } from './sales-order-detail.component';
import { SalesOrderPopupComponent, SalesOrderDialogComponent } from './sales-order-dialog.component';
import { SalesOrderDeletePopupComponent } from './sales-order-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SalesOrderResolvePagingParams implements Resolve<any> {

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

export const salesOrderRoute: Routes = [
  {
    path: 'sales-order',
    component: SalesOrderComponent,
    resolve: {
      'pagingParams': SalesOrderResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.salesOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'sales-order-new',
    component: SalesOrderDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.salesOrder.home.title'
    },
  }, {
    path: 'sales-order/:id',
    component: SalesOrderDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.salesOrder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const salesOrderPopupRoute: Routes = [
  {
    path: 'sales-order/:id/edit',
    component: SalesOrderPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.salesOrder.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'sales-order/:id/delete',
    component: SalesOrderDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.salesOrder.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
