import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductImageDescriptionComponent } from './product-image-description.component';
import { ProductImageDescriptionDetailComponent } from './product-image-description-detail.component';
import { ProductImageDescriptionPopupComponent, ProductImageDescriptionDialogComponent } from './product-image-description-dialog.component';
import { ProductImageDescriptionDeletePopupComponent } from './product-image-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductImageDescriptionResolvePagingParams implements Resolve<any> {

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

export const productImageDescriptionRoute: Routes = [
  {
    path: 'product-image-description',
    component: ProductImageDescriptionComponent,
    resolve: {
      'pagingParams': ProductImageDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImageDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-image-description-new',
    component: ProductImageDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImageDescription.home.title'
    },
  }, {
    path: 'product-image-description/:id',
    component: ProductImageDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImageDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productImageDescriptionPopupRoute: Routes = [
  {
    path: 'product-image-description/:id/edit',
    component: ProductImageDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImageDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-image-description/:id/delete',
    component: ProductImageDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImageDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
