import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ProductReviewDescriptionComponent } from './product-review-description.component';
import { ProductReviewDescriptionDetailComponent } from './product-review-description-detail.component';
import { ProductReviewDescriptionPopupComponent, ProductReviewDescriptionDialogComponent } from './product-review-description-dialog.component';
import { ProductReviewDescriptionDeletePopupComponent } from './product-review-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ProductReviewDescriptionResolvePagingParams implements Resolve<any> {

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

export const productReviewDescriptionRoute: Routes = [
  {
    path: 'product-review-description',
    component: ProductReviewDescriptionComponent,
    resolve: {
      'pagingParams': ProductReviewDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReviewDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'product-review-description-new',
    component: ProductReviewDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReviewDescription.home.title'
    },
  }, {
    path: 'product-review-description/:id',
    component: ProductReviewDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReviewDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productReviewDescriptionPopupRoute: Routes = [
  {
    path: 'product-review-description/:id/edit',
    component: ProductReviewDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReviewDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'product-review-description/:id/delete',
    component: ProductReviewDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.productReviewDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
