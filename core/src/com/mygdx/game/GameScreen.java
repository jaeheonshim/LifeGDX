package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Vector;

public class GameScreen implements Screen {
    Viewport viewport;

    Grid grid;

    ShapeRenderer renderer;

    float cellSize = 10;
    float timer;

    float speed = 0.2f;

    float clickDelayTimer = 0;

    float gridWidth = 1;

    @Override
    public void show() {
        grid = new Grid(20, 20);
        grid.getCell(0, 0).populate();

        renderer = new ShapeRenderer();
        viewport = new FitViewport(cellSize * grid.getGrid().length, cellSize * grid.getGrid()[0].length);
    }

    @Override
    public void render(float delta) {
        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            timer += delta;
        }
        if (timer > speed) {
            grid.execute();
            timer = 0;
        }

        clickDelayTimer += delta;

        if(Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            Vector2 touchlocation =  viewport.unproject(new Vector2(x, y));
            System.out.println((int) touchlocation.x / cellSize);

            if(((int) (touchlocation.x / cellSize) >= 0 && (int) (touchlocation.x / cellSize) < grid.getWidth()) && ((int) (touchlocation.y / cellSize) >= 0 && (int) (touchlocation.y / cellSize) < grid.getHeight())) {
                Cell clickedCell = grid.getCell((int) (touchlocation.x / cellSize), (int) (touchlocation.y / cellSize));
                if(clickedCell.isPopulated() && clickDelayTimer > 0.05) {
                    clickedCell.kill();
                } else if(clickDelayTimer > 0.05) {
                    clickedCell.populate();
                }

                clickDelayTimer = 0;
            }
        }

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        int x = 0, y = 0;

        renderer.setColor(Color.DARK_GRAY);
        renderer.rectLine(0, 0, 0, viewport.getWorldHeight(), 1f);
        renderer.setColor(Color.WHITE);

        for(Cell[] cellRow : grid.getGrid()) {
            for(Cell cell : cellRow) {
                if(cell.isPopulated()) {
                    renderer.rect(x, y, cellSize, cellSize);
                    x += cellSize;
                } else {
                    x += cellSize;
                }
                renderer.setColor(Color.DARK_GRAY);
                renderer.rectLine(x, 0, x, viewport.getWorldHeight(), 1f);
                renderer.rectLine(0, y, viewport.getWorldWidth(), y, 1f);
                renderer.setColor(Color.WHITE);
            }
            y += cellSize;
            x = 0;
        }

        renderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
