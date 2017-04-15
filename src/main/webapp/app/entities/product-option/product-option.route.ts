import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductOptionComponent } from './product-option.component';
import { ProductOptionDetailComponent } from './product-option-detail.component';
import { ProductOptionPopupComponent, ProductOptionDialogComponent } from './product-option-dialog.component';
import { ProductOptionDeletePopupComponent } from './product-option-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductOptionResolvePagingParams implements Resolve<any> {

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

export const productOptionRoute: Routes = [
  {
    path: 'product-option',
    component: ProductOptionComponent,
    resolve: {
      'pagingParams': ProductOptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-option-new',
    component: ProductOptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOption.home.title'
    },
  }, {
    path: 'product-option/:id',
    component: ProductOptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productOptionPopupRoute: Routes = [
  {
    path: 'product-option/:id/edit',
    component: ProductOptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-option/:id/delete',
    component: ProductOptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOption.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
