import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DigitalProductComponent } from './digital-product.component';
import { DigitalProductDetailComponent } from './digital-product-detail.component';
import { DigitalProductPopupComponent, DigitalProductDialogComponent } from './digital-product-dialog.component';
import { DigitalProductDeletePopupComponent } from './digital-product-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class DigitalProductResolvePagingParams implements Resolve<any> {

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

export const digitalProductRoute: Routes = [
  {
    path: 'digital-product',
    component: DigitalProductComponent,
    resolve: {
      'pagingParams': DigitalProductResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.digitalProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'digital-product-new',
    component: DigitalProductDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.digitalProduct.home.title'
    },
  }, {
    path: 'digital-product/:id',
    component: DigitalProductDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.digitalProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const digitalProductPopupRoute: Routes = [
  {
    path: 'digital-product/:id/edit',
    component: DigitalProductPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.digitalProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'digital-product/:id/delete',
    component: DigitalProductDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.digitalProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
