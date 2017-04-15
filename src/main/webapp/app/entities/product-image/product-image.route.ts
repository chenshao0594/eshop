import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductImageComponent } from './product-image.component';
import { ProductImageDetailComponent } from './product-image-detail.component';
import { ProductImagePopupComponent, ProductImageDialogComponent } from './product-image-dialog.component';
import { ProductImageDeletePopupComponent } from './product-image-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductImageResolvePagingParams implements Resolve<any> {

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

export const productImageRoute: Routes = [
  {
    path: 'product-image',
    component: ProductImageComponent,
    resolve: {
      'pagingParams': ProductImageResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-image-new',
    component: ProductImageDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImage.home.title'
    },
  }, {
    path: 'product-image/:id',
    component: ProductImageDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productImagePopupRoute: Routes = [
  {
    path: 'product-image/:id/edit',
    component: ProductImagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-image/:id/delete',
    component: ProductImageDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
