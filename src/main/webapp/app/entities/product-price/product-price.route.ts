import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductPriceComponent } from './product-price.component';
import { ProductPriceDetailComponent } from './product-price-detail.component';
import { ProductPricePopupComponent, ProductPriceDialogComponent } from './product-price-dialog.component';
import { ProductPriceDeletePopupComponent } from './product-price-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductPriceResolvePagingParams implements Resolve<any> {

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

export const productPriceRoute: Routes = [
  {
    path: 'product-price',
    component: ProductPriceComponent,
    resolve: {
      'pagingParams': ProductPriceResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-price/:id',
    component: ProductPriceDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'product-price-new',
    component: ProductPriceDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'product-price/:id/edit',
    component: ProductPriceDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productPricePopupRoute: Routes = [
  
  
  {
    path: 'product-price/:id/delete',
    component: ProductPriceDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPrice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
