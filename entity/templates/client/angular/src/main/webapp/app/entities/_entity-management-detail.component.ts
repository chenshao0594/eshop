<%#
 Copyright 2013-2017 the original author or authors.

 This file is part of the JHipster project, see https://jhipster.github.io/
 for more information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-%>
<%_
const i18nToLoad = [entityInstance];
for (const idx in fields) {
    if (fields[idx].fieldIsEnum == true) {
        i18nToLoad.push(fields[idx].enumInstance);
    }
}
_%>
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager <% if (enableTranslation) { %>, JhiLanguageService<% } %> <% if (fieldsContainBlob) { %>, DataUtils<% } %>, AlertService } from 'ng-jhipster';

import { <%= entityAngularName %> } from './<%= entityFileName %>.model';
import { <%= entityAngularName %>Service } from './<%= entityFileName %>.service';

@Component({
    selector: '<%= jhiPrefix %>-<%= entityFileName %>-detail',
    templateUrl: './<%= entityFileName %>-detail.component.html'
})
export class <%= entityAngularName %>DetailComponent implements OnInit, OnDestroy {

    <%= entityInstance %>: <%= entityAngularName %>;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        <%_ if (enableTranslation) { _%>
        private jhiLanguageService: JhiLanguageService,
        <%_ } _%>
        <%_ if (fieldsContainBlob) { _%>
        private dataUtils: DataUtils,
        <%_ } _%>
        private <%= entityInstance %>Service: <%= entityAngularName %>Service,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        <%_ if (enableTranslation) { _%>
        this.jhiLanguageService.setLocations(<%- toArrayString(i18nToLoad) %>);
        <%_ } _%>
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeIn<%= entityClassPlural %>();
    }

    load(id) {
        this.<%= entityInstance %>Service.find(id).subscribe((<%= entityInstance %>) => {
            this.<%= entityInstance %> = <%= entityInstance %>;
        });
    }
    <%_ if (fieldsContainBlob) { _%>
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    <%_ } _%>
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.<%= entityInstance %>.id !== undefined) {
            this.<%= entityInstance %>Service.update(this.<%= entityInstance %>)
                .subscribe((res: <%= entityAngularName %>) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.<%= entityInstance %>Service.create(this.<%= entityInstance %>)
                .subscribe((res: <%= entityAngularName %>) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: <%= entityAngularName %>) {
        this.eventManager.broadcast({ name: '<%= entityInstance %>Modification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeIn<%= entityClassPlural %>() {
        this.eventSubscriber = this.eventManager.subscribe('<%= entityInstance %>ListModification', (response) => this.load(this.<%= entityInstance %>.id));
    }
}