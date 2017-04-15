import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderProductDownloadComponent } from './order-product-download.component';
import { OrderProductDownloadDetailComponent } from './order-product-download-detail.component';
import { OrderProductDownloadPopupComponent, OrderProductDownloadDialogComponent } from './order-product-download-dialog.component';
import { OrderProductDownloadDeletePopupComponent } from './order-product-download-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderProductDownloadResolvePagingParams implements Resolve<any> {

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

export const orderProductDownloadRoute: Routes = [
  {
    path: 'order-product-download',
    component: OrderProductDownloadComponent,
    resolve: {
      'pagingParams': OrderProductDownloadResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductDownload.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-product-download-new',
    component: OrderProductDownloadDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductDownload.home.title'
    },
  }, {
    path: 'order-product-download/:id',
    component: OrderProductDownloadDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductDownload.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderProductDownloadPopupRoute: Routes = [
  {
    path: 'order-product-download/:id/edit',
    component: OrderProductDownloadPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductDownload.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-product-download/:id/delete',
    component: OrderProductDownloadDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductDownload.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
