import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderStatusHistoryComponent } from './order-status-history.component';
import { OrderStatusHistoryDetailComponent } from './order-status-history-detail.component';
import { OrderStatusHistoryPopupComponent, OrderStatusHistoryDialogComponent } from './order-status-history-dialog.component';
import { OrderStatusHistoryDeletePopupComponent } from './order-status-history-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderStatusHistoryResolvePagingParams implements Resolve<any> {

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

export const orderStatusHistoryRoute: Routes = [
  {
    path: 'order-status-history',
    component: OrderStatusHistoryComponent,
    resolve: {
      'pagingParams': OrderStatusHistoryResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderStatusHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-status-history-new',
    component: OrderStatusHistoryDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderStatusHistory.home.title'
    },
  }, {
    path: 'order-status-history/:id',
    component: OrderStatusHistoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderStatusHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderStatusHistoryPopupRoute: Routes = [
  {
    path: 'order-status-history/:id/edit',
    component: OrderStatusHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderStatusHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-status-history/:id/delete',
    component: OrderStatusHistoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderStatusHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
