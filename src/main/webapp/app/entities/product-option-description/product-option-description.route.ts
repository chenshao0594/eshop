import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductOptionDescriptionComponent } from './product-option-description.component';
import { ProductOptionDescriptionDetailComponent } from './product-option-description-detail.component';
import { ProductOptionDescriptionPopupComponent, ProductOptionDescriptionDialogComponent } from './product-option-description-dialog.component';
import { ProductOptionDescriptionDeletePopupComponent } from './product-option-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductOptionDescriptionResolvePagingParams implements Resolve<any> {

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

export const productOptionDescriptionRoute: Routes = [
  {
    path: 'product-option-description',
    component: ProductOptionDescriptionComponent,
    resolve: {
      'pagingParams': ProductOptionDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-option-description-new',
    component: ProductOptionDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionDescription.home.title'
    },
  }, {
    path: 'product-option-description/:id',
    component: ProductOptionDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productOptionDescriptionPopupRoute: Routes = [
  {
    path: 'product-option-description/:id/edit',
    component: ProductOptionDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-option-description/:id/delete',
    component: ProductOptionDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productOptionDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
