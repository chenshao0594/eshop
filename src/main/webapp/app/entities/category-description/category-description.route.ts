import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CategoryDescriptionComponent } from './category-description.component';
import { CategoryDescriptionDetailComponent } from './category-description-detail.component';
import { CategoryDescriptionPopupComponent, CategoryDescriptionDialogComponent } from './category-description-dialog.component';
import { CategoryDescriptionDeletePopupComponent } from './category-description-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CategoryDescriptionResolvePagingParams implements Resolve<any> {

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

export const categoryDescriptionRoute: Routes = [
  {
    path: 'category-description',
    component: CategoryDescriptionComponent,
    resolve: {
      'pagingParams': CategoryDescriptionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.categoryDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'category-description-new',
    component: CategoryDescriptionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.categoryDescription.home.title'
    },
  }, {
    path: 'category-description/:id',
    component: CategoryDescriptionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.categoryDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const categoryDescriptionPopupRoute: Routes = [
  {
    path: 'category-description/:id/edit',
    component: CategoryDescriptionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.categoryDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'category-description/:id/delete',
    component: CategoryDescriptionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.categoryDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
