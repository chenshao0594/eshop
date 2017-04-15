import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductDescriptionComponent } from './product-description.component';
import { ProductDescriptionDetailComponent } from './product-description-detail.component';
import { ProductDescriptionPopupComponent, ProductDescriptionDialogComponent } from './product-description-dialog.component';
import { ProductDescriptionDeletePopupComponent } from './product-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductDescriptionResolvePagingParams implements Resolve<any> {

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

export const productDescriptionRoute: Routes = [
  {
    path: 'product-description',
    component: ProductDescriptionComponent,
    resolve: {
      'pagingParams': ProductDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-description-new',
    component: ProductDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productDescription.home.title'
    },
  }, {
    path: 'product-description/:id',
    component: ProductDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productDescriptionPopupRoute: Routes = [
  {
    path: 'product-description/:id/edit',
    component: ProductDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-description/:id/delete',
    component: ProductDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
