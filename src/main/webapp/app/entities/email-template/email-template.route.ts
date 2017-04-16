import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EmailTemplateComponent } from './email-template.component';
import { EmailTemplateDetailComponent } from './email-template-detail.component';
import { EmailTemplatePopupComponent, EmailTemplateDialogComponent } from './email-template-dialog.component';
import { EmailTemplateDeletePopupComponent } from './email-template-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class EmailTemplateResolvePagingParams implements Resolve<any> {

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

export const emailTemplateRoute: Routes = [
  {
    path: 'email-template',
    component: EmailTemplateComponent,
    resolve: {
      'pagingParams': EmailTemplateResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'email-template-new',
    component: EmailTemplateDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailTemplate.home.title'
    },
  }, {
    path: 'email-template/:id',
    component: EmailTemplateDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const emailTemplatePopupRoute: Routes = [
  {
    path: 'email-template/:id/edit',
    component: EmailTemplatePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailTemplate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'email-template/:id/delete',
    component: EmailTemplateDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.emailTemplate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
