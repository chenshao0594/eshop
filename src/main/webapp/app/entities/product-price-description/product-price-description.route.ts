import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductPriceDescriptionComponent } from './product-price-description.component';
import { ProductPriceDescriptionDetailComponent } from './product-price-description-detail.component';
import { ProductPriceDescriptionPopupComponent, ProductPriceDescriptionDialogComponent } from './product-price-description-dialog.component';
import { ProductPriceDescriptionDeletePopupComponent } from './product-price-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductPriceDescriptionResolvePagingParams implements Resolve<any> {

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

export const productPriceDescriptionRoute: Routes = [
  {
    path: 'product-price-description',
    component: ProductPriceDescriptionComponent,
    resolve: {
      'pagingParams': ProductPriceDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPriceDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-price-description-new',
    component: ProductPriceDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPriceDescription.home.title'
    },
  }, {
    path: 'product-price-description/:id',
    component: ProductPriceDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPriceDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productPriceDescriptionPopupRoute: Routes = [
  {
    path: 'product-price-description/:id/edit',
    component: ProductPriceDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPriceDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-price-description/:id/delete',
    component: ProductPriceDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productPriceDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
