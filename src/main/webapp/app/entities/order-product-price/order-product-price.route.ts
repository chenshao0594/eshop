import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderProductPriceComponent } from './order-product-price.component';
import { OrderProductPriceDetailComponent } from './order-product-price-detail.component';
import { OrderProductPricePopupComponent, OrderProductPriceDialogComponent } from './order-product-price-dialog.component';
import { OrderProductPriceDeletePopupComponent } from './order-product-price-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderProductPriceResolvePagingParams implements Resolve<any> {

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

export const orderProductPriceRoute: Routes = [
  {
    path: 'order-product-price',
    component: OrderProductPriceComponent,
    resolve: {
      'pagingParams': OrderProductPriceResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-product-price-new',
    component: OrderProductPriceDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductPrice.home.title'
    },
  }, {
    path: 'order-product-price/:id',
    component: OrderProductPriceDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderProductPricePopupRoute: Routes = [
  {
    path: 'order-product-price/:id/edit',
    component: OrderProductPricePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductPrice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-product-price/:id/delete',
    component: OrderProductPriceDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductPrice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
