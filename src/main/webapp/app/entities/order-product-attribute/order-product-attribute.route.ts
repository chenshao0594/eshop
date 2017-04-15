import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { OrderProductAttributeComponent } from './order-product-attribute.component';
import { OrderProductAttributeDetailComponent } from './order-product-attribute-detail.component';
import { OrderProductAttributePopupComponent, OrderProductAttributeDialogComponent } from './order-product-attribute-dialog.component';
import { OrderProductAttributeDeletePopupComponent } from './order-product-attribute-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OrderProductAttributeResolvePagingParams implements Resolve<any> {

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

export const orderProductAttributeRoute: Routes = [
  {
    path: 'order-product-attribute',
    component: OrderProductAttributeComponent,
    resolve: {
      'pagingParams': OrderProductAttributeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'order-product-attribute-new',
    component: OrderProductAttributeDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductAttribute.home.title'
    },
  }, {
    path: 'order-product-attribute/:id',
    component: OrderProductAttributeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderProductAttributePopupRoute: Routes = [
  {
    path: 'order-product-attribute/:id/edit',
    component: OrderProductAttributePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'order-product-attribute/:id/delete',
    component: OrderProductAttributeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.orderProductAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
