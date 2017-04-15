import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductOptionValueComponent } from './product-option-value.component';
import { ProductOptionValueDetailComponent } from './product-option-value-detail.component';
import { ProductOptionValuePopupComponent, ProductOptionValueDialogComponent } from './product-option-value-dialog.component';
import { ProductOptionValueDeletePopupComponent } from './product-option-value-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductOptionValueResolvePagingParams implements Resolve<any> {

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

export const productOptionValueRoute: Routes = [
  {
    path: 'product-option-value',
    component: ProductOptionValueComponent,
    resolve: {
      'pagingParams': ProductOptionValueResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-option-value-new',
    component: ProductOptionValueDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValue.home.title'
    },
  }, {
    path: 'product-option-value/:id',
    component: ProductOptionValueDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productOptionValuePopupRoute: Routes = [
  {
    path: 'product-option-value/:id/edit',
    component: ProductOptionValuePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-option-value/:id/delete',
    component: ProductOptionValueDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionValue.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
