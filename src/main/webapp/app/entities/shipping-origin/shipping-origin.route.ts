import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ShippingOriginComponent } from './shipping-origin.component';
import { ShippingOriginDetailComponent } from './shipping-origin-detail.component';
import { ShippingOriginPopupComponent, ShippingOriginDialogComponent } from './shipping-origin-dialog.component';
import { ShippingOriginDeletePopupComponent } from './shipping-origin-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ShippingOriginResolvePagingParams implements Resolve<any> {

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

export const shippingOriginRoute: Routes = [
  {
    path: 'shipping-origin',
    component: ShippingOriginComponent,
    resolve: {
      'pagingParams': ShippingOriginResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shippingOrigin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'shipping-origin-new',
    component: ShippingOriginDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shippingOrigin.home.title'
    },
  }, {
    path: 'shipping-origin/:id',
    component: ShippingOriginDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shippingOrigin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shippingOriginPopupRoute: Routes = [
  {
    path: 'shipping-origin/:id/edit',
    component: ShippingOriginPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shippingOrigin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'shipping-origin/:id/delete',
    component: ShippingOriginDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shippingOrigin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
