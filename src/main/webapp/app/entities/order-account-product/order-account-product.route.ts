import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderAccountProductComponent } from './order-account-product.component';
import { OrderAccountProductDetailComponent } from './order-account-product-detail.component';
import { OrderAccountProductPopupComponent, OrderAccountProductDialogComponent } from './order-account-product-dialog.component';
import { OrderAccountProductDeletePopupComponent } from './order-account-product-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderAccountProductResolvePagingParams implements Resolve<any> {

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

export const orderAccountProductRoute: Routes = [
  {
    path: 'order-account-product',
    component: OrderAccountProductComponent,
    resolve: {
      'pagingParams': OrderAccountProductResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccountProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-account-product-new',
    component: OrderAccountProductDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccountProduct.home.title'
    },
  }, {
    path: 'order-account-product/:id',
    component: OrderAccountProductDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccountProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderAccountProductPopupRoute: Routes = [
  {
    path: 'order-account-product/:id/edit',
    component: OrderAccountProductPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccountProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-account-product/:id/delete',
    component: OrderAccountProductDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderAccountProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
