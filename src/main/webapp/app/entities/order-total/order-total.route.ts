import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderTotalComponent } from './order-total.component';
import { OrderTotalDetailComponent } from './order-total-detail.component';
import { OrderTotalPopupComponent, OrderTotalDialogComponent } from './order-total-dialog.component';
import { OrderTotalDeletePopupComponent } from './order-total-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderTotalResolvePagingParams implements Resolve<any> {

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

export const orderTotalRoute: Routes = [
  {
    path: 'order-total',
    component: OrderTotalComponent,
    resolve: {
      'pagingParams': OrderTotalResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderTotal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-total-new',
    component: OrderTotalDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderTotal.home.title'
    },
  }, {
    path: 'order-total/:id',
    component: OrderTotalDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderTotal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderTotalPopupRoute: Routes = [
  {
    path: 'order-total/:id/edit',
    component: OrderTotalPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderTotal.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-total/:id/delete',
    component: OrderTotalDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderTotal.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
