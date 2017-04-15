import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CategoryDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/category-description/category-description-detail.component';
import { CategoryDescriptionService } from '../../../../../../main/webapp/app/entities/category-description/category-description.service';
import { CategoryDescription } from '../../../../../../main/webapp/app/entities/category-description/category-description.model';

describe('Component Tests', () => {

    describe('CategoryDescription Management Detail Component', () => {
        let comp: CategoryDescriptionDetailComponent;
        let fixture: ComponentFixture<CategoryDescriptionDetailComponent>;
        let service: CategoryDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CategoryDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CategoryDescriptionService,
                    EventManager
                ]
            }).overrideComponent(CategoryDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategoryDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoryDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CategoryDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.categoryDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
