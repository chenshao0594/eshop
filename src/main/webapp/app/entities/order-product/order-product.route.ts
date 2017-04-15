import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderProductComponent } from './order-product.component';
import { OrderProductDetailComponent } from './order-product-detail.component';
import { OrderProductPopupComponent, OrderProductDialogComponent } from './order-product-dialog.component';
import { OrderProductDeletePopupComponent } from './order-product-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderProductResolvePagingParams implements Resolve<any> {

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

export const orderProductRoute: Routes = [
  {
    path: 'order-product',
    component: OrderProductComponent,
    resolve: {
      'pagingParams': OrderProductResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-product-new',
    component: OrderProductDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProduct.home.title'
    },
  }, {
    path: 'order-product/:id',
    component: OrderProductDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderProductPopupRoute: Routes = [
  {
    path: 'order-product/:id/edit',
    component: OrderProductPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-product/:id/delete',
    component: OrderProductDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
